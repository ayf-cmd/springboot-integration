package com.mybatis.config.page;

import com.github.pagehelper.Page;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageVO<T> implements Serializable {
    private Pagination pagination;
    private List<T> list;

    /**
     * @param page
     * @param tClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> PageVO<T> build(Page<E> page, Class<T> tClass) {
        Pagination pagination = Pagination.builder().current(page.getPageNum()).pageSize(page.getPageSize())
                .pages(page.getPages()).total(page.getTotal()).build();
        List<E> sourceList = page.getResult();
        List<T> target;
        if (!CollectionUtils.isEmpty(sourceList)) {
            target = MapperUtils.INSTANCE.mapAsList(tClass, sourceList);
        } else {
            target = new ArrayList<>(0);
        }
        return PageVO.<T>builder().list(target).pagination(pagination).build();
    }

    public static <T, E> PageVO<T> build(Page<E> page, List<T> dataList) {
        Pagination pagination = Pagination.builder().current(page.getPageNum()).pageSize(page.getPageSize())
                .pages(page.getPages()).total(page.getTotal()).build();
        if (dataList == null) {
            dataList = new ArrayList<>(0);
        }
        return PageVO.<T>builder().list(dataList).pagination(pagination).build();
    }

    public static int getPages(long total, int pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        return (int) (total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1));
    }

}
