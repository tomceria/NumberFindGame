package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PagedResult<T> implements Serializable {
	private ArrayList<T> result;
	public int currentPage;
	int pageSize;
	int count;
	public int totalPage;
	public boolean willPrev;
	public boolean willNext;

	public PagedResult(ArrayList<T> result, int currentPage, int pageSize, int count) {
		this.setResult(result);
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.count = count;

		this.totalPage = (int) Math.ceil(count / pageSize);
		this.willPrev = currentPage > 1;
		this.willNext = currentPage < totalPage;
	}

	public ArrayList<T> getResult() {
		return result;
	}

	public void setResult(ArrayList<T> result) {
		this.result = result;
	}
}
