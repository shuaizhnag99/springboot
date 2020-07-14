package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;

@Transfer(name = "分页查询模型")
public class PagedQueryDto extends CheckedTransModel{
    private static final long serialVersionUID = 6754938768395500648L;
    @ParameterDescripor(Index = "page", Descript = "查询页码")
    private Long currentPage;
    @ParameterDescripor(Index = "size", Descript = "页长")
    private Long size;

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
