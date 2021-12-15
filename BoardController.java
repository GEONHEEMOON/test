package org.mvc.controller;

import java.util.List;

import org.mvc.bean.BoardDTO;
import org.mvc.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Log4j
@RequestMapping("/board/")
public class BoardController {

	@Autowired //controller.xml 에서 객체를 대입받는다.(의존성주입)
	private BoardService service; //BoardServiceImpl 객체가 컨트롤에서 대입된다.

	
	@RequestMapping("list")
	public String list(Model model) {
		List<BoardDTO> list = service.getList(); //getList  불러와 list 객체로 저장
		log.info(""+list);
		model.addAttribute("list",list); //list는 attribute 저장 
		
		return "board/list";
//	전체 불러오고 다시 리턴 
//		BoardController
//			BoardService 인터페이스
//				BoardServiceImpl 클래스
//					BoardMapper 인터페이스
//						BoardMapper.xml 마이바티스
	}
	@RequestMapping("write")
	public String write() {
		log.info("============/board/write");
		return "board/write";
	}
	
	@RequestMapping("writePro")
	public String writhePro(BoardDTO dto , Model model) {
//		() -> 매개변수 받아올 값 /write.jsp/title/content/.....
		model.addAttribute("result", service.boardWrite(dto));
		
//	  파라미터 dto 에 boardWrtierd  추가 
		return "board/writePro"; 
	}
	
	
	
	
	
	@RequestMapping("readcount") //조회수 증가 
	public String readcount(Long bno , RedirectAttributes rttr) { //글번호 , 리다렉트 
		log.info("===========/board/readcount?bno="+bno);  
		service.boardReadCount(bno);

		rttr.addAttribute("bno", bno); //model이랑 같은 기능 
//		rttr.addFlashAttribute(rttr); //일회용 rttr.addAttribute랑 다른기능
		return  "redirect:/board/content"; 
//	readcount -> content 
	
	}	
	@RequestMapping("content") 
	public String content(Long bno, Model model) { //파라미터 (bno-글번호) 값을 가지고 오는 
//		BoardService.java 이동 /public BoardDTO getBnoRead(Long bno); 
		log.info("===========/board/content?bno="+bno);  
//		-----------------------
		service.boardReadCount(bno); //조회수 증가
		model.addAttribute("boardDTO", service.getBnoRead(bno)); //호출
//		글정보 가지고 오고 그다음 조회수 증가
		return "board/content";
	}
	
	
	
	
	
	
	
	
	@RequestMapping("update")
	public String update (BoardDTO boardDTO) {
//		 dto로 받은값은 view까지 이동 
		log.info("===========/board/update?bno="+boardDTO.getBno());
		return "board/update";
	}
	@RequestMapping("updatePro")
	public String updatePro (BoardDTO dto , Model model) {
		log.info("===========/board/updatePro");
		model.addAttribute("result", service.boardUpdate(dto));
		return "board/updatePro";
	}
	@RequestMapping("delete")
	public String delete (@ModelAttribute("bno") Long bno) {
		log.info("===========/board/delete?bno"+bno);
		return "board/delete";
	}
	@RequestMapping("deletePro")
	public String deletePro(Long bno , Model model) {
		log.info("===========/board/delete?bno"+bno );
		model.addAttribute("result", service.boardDelete(bno));
		return "board/deletePro";
	}
}
