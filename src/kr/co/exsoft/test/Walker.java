package kr.co.exsoft.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 
 * glob패턴으로 파일 검색 , 용량산정, 사이즈 구하기
 * 
 * @author exsoft
 *
 */

public class Walker  implements FileVisitor {

    private final PathMatcher matcher;

    private long totalFileSize = 0L;
    private int totalFileCnt = 0;
    
    public Walker(String glob) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);

        System.out.println("===========================================");
        System.out.println(matcher);
        System.out.println("===========================================");

    }

    void search(Path file) throws IOException {
        Path name = file.getFileName();
                        
        if (name != null && matcher.matches(name)) {
        	//해당하는 파일 목록을 뿌려주고 싶을때 주석제거
//            System.out.println("** found: " + name + " in " + file.toRealPath().toString());
            totalFileSize += file.toFile().length();
            totalFileCnt ++;
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
    	System.out.println("# Search to ---> " + (Path) dir);
        return FileVisitResult.CONTINUE;

    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        search((Path) file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

	public long getTotalFileSize() {
		long rtnSize = totalFileSize;
		totalFileSize = 0L;
		return rtnSize;
	}

	public int getTotalFileCnt() {
		int rtnCnt = totalFileCnt;
		totalFileCnt = 0;
		return rtnCnt;
	}
}
