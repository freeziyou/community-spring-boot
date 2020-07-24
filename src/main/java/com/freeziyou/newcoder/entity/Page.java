package com.freeziyou.newcoder.entity;

/**
 * @author Dylan Guo
 * @date 7/24/2020 15:46
 * @description 封装分页相关信息
 */
public class Page {
    /**
     * 当前页码
     */
    private Integer current = 1;
    /**
     * 显示上限
     */
    private Integer limit = 10;
    /**
     * 数据总数(用于计算总页数)
     */
    private Integer rows;
    /**
     * 查询路径(用于复用分页链接)
     */
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页起始行
     *
     * @return 返回起始行
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**
     * 获取总共页数
     *
     * @return 返回总共页数
     */
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码
     *
     * @return 返回起始页
     */
    public int getFrom() {
        int from = current - 2;
        return Math.max(from, 1);
    }

    /**
     * 获取结束页
     *
     * @return 返回结束页
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return Math.min(to, total);
    }
}
