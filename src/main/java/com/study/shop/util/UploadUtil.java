package com.study.shop.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.study.shop.item.vo.ImgVO;

//파일 업로드기능 메소드로 만들어두고 사용하기
public class UploadUtil {
	
	
	//단일 파일 업로드 메소드
	public static ImgVO uploadFile(MultipartFile img) {
		//DB에 등록하기 위한 데이터를 가져갈 imgVO(return으로)
		ImgVO imgVO = null;
		
		if(!img.isEmpty()) {
			imgVO = new ImgVO();
			
			//mainImg의 원본 파일명값 (첨부한 파일명) (확장자명을 추출하기 위해 필요)
			String originFileName = img.getOriginalFilename();
			//서버에 올라갈 파일명 생성 (서버상에서 파일명이 중복되지 않도록)
			String uuid = UUID.randomUUID().toString(); //랜덤한 문자열 생성
			//확장자 추출 (원본파일명.jpg/png >> 서버파일명.jpg/png로 만들어주기 위해)
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			//최종 서버 파일명 생성
			String attachedFileName = uuid + extension;
			//파일 업로드 위치 >> 상수 관리용 클래스(ConstVariable)에 위치
			
			//파일 업로드 실행
			try {
				File file = new File(ConstVariable.UPLOAD_PATH + attachedFileName);
				img.transferTo(file);
				imgVO.setOriginFileName(originFileName);
				imgVO.setAttachedFileName(attachedFileName);
				imgVO.setIsMain("Y");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return imgVO;
		
	}
	//다중 파일 업로드 메소드
	public static List<ImgVO> multiFileUpload(MultipartFile[] imgs) {
		List<ImgVO> result = new ArrayList<>();
		
		for(MultipartFile img : imgs) {
			ImgVO imgVO = uploadFile(img);
			imgVO.setIsMain("N");
			result.add(imgVO);
		}
		return result;
	}
}
