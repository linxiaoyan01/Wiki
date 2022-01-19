package top.kaluna.wiki.req;

/**
 * @author Yuery
 * @date 2022/1/16/0016 - 14:40
 */
public class EbookQueryReq extends PageReq{
    private int id;

    private Long categoryId2;
    private String name;

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EbookQueryReq{");
        sb.append("id=").append(id);
        sb.append(", categoryId2=").append(categoryId2);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
