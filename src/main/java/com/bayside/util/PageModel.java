package com.bayside.util;

import java.util.Collection;
import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.github.pagehelper.Page;

public class PageModel {

	//结果集    
    private List<SubJectArticleBo> list;    
        
    //查询记录数    
    private int totalRecords;    
        
    //每页多少条数据    
    private int pageSize;    
        
    //第几页    
    private int pageNo;  
    
    //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
    private int pages;
    private List<Integer> arraypage;

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNo == 1;
        isLastPage = pageNo == pages;
        hasPreviousPage = pageNo > 1;
        hasNextPage = pageNo < pages;
    }
    
    
    
    /**  
     * 总页数  
     * @return  
     */    
    public int getTotalPages() {    
    	pages = (totalRecords + pageSize - 1) / pageSize;
        return (totalRecords + pageSize - 1) / pageSize;    
    }    
        
	/**  
     * 取得首页  
     * @return  
     */    
    public int getTopPageNo() {    
        return 1;    
    }    
    /**  
     * 上一页  
     * @return  
     */    
    public int getPreviousPageNo() {    
        if (pageNo <= 1) {    
            return 1;    
        }    
        return pageNo - 1;    
    }    
        
    /**  
     * 下一页  
     * @return  
     */    
    public int getNextPageNo() {    
        if (pageNo >= getBottomPageNo()) {    
            return getBottomPageNo();    
        }    
        return pageNo + 1;      
    }    
    /**  
     * 取得尾页  
     * @return  
     */    
    public int getBottomPageNo() {    
        return getTotalPages();    
    }    
        
    public List<SubJectArticleBo> getList() {    
        return list;    
    }    
    
    public void setList(List<SubJectArticleBo> list) {    
        this.list = list;    
    }    
    
    public int getTotalRecords() {    
        return totalRecords;    
    }    
    
    public void setTotalRecords(int totalRecords) {    
        this.totalRecords = totalRecords;    
    }    
    
    public int getPageSize() {    
        return pageSize;    
    }    
    
    public void setPageSize(int pageSize) {    
        this.pageSize = pageSize;    
    }    
    
    public int getPageNo() {    
        return pageNo;    
    }    
    
    public void setPageNo(int pageNo) {    
        this.pageNo = pageNo;    
    }



	public boolean isFirstPage() {
		return isFirstPage;
	}



	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}



	public boolean isLastPage() {
		return isLastPage;
	}



	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}



	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}



	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}



	public boolean isHasNextPage() {
		return hasNextPage;
	}



	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}



	public int getPages() {
		return pages;
	}



	public void setPages(int pages) {
		this.pages = pages;
	}



	public List<Integer> getArraypage() {
		return arraypage;
	}



	public void setArraypage(List<Integer> arraypage) {
		this.arraypage = arraypage;
	}



	
	
	

	/*public void setTotalPages(int totalPages) {
		
	}   */ 
}
