package top.kaluna.wiki.req;

import javax.validation.constraints.NotNull;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:30
 */
public class DocSaveReq {
    private Long id;

    @NotNull(message="[电子书id]不能为空")
    private Long ebookId;

    @NotNull(message="[父id]不能为空")
    private Long parent;

    @NotNull(message="[名称]不能为空")
    private String name;

    @NotNull(message="[顺序]不能为空")
    private Integer sort;

    @NotNull(message = "[内容]不能为空")
    private String content;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocSaveReq{");
        sb.append("id=").append(id);
        sb.append(", ebookId=").append(ebookId);
        sb.append(", parent=").append(parent);
        sb.append(", name='").append(name).append('\'');
        sb.append(", sort=").append(sort);
        sb.append(", content='").append(content).append('\'');
        sb.append(", viewCount=").append(viewCount);
        sb.append(", voteCount=").append(voteCount);
        sb.append('}');
        return sb.toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private Integer viewCount;

    private Integer voteCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

}
