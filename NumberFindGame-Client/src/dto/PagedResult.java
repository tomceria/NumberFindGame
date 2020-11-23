package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PagedResult<T> implements Serializable {
    ArrayList<T> result;
    int currentPage;
    int pageSize;
    int count;
    int totalPage;
    boolean willPrev;
    boolean willNext;

    public PagedResult(ArrayList<T> result, int currentPage, int pageSize, int count) {
        this.result = result;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.count = count;

        this.totalPage = (int) Math.ceil(count / pageSize);
        this.willPrev = currentPage > 1;
        this.willNext = currentPage < totalPage;
    }
}
