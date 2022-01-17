package top.kaluna.wiki.req;

/**
 * @author Yuery
 * @date 2022/1/16/0016 - 14:40
 */
public class EbookReq extends PageReq{
    private int id;

    private String name;

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
}
