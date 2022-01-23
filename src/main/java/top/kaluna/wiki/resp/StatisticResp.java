package top.kaluna.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:34
 */
public class StatisticResp {
    @JsonFormat(pattern = "MM-dd",timezone = "GMT+8")
    private Date date;
    private int viewCount;
    private int voteCount;
    private int viewIncrease;
    private int voteIncrease;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getViewIncrease() {
        return viewIncrease;
    }

    public void setViewIncrease(int viewIncrease) {
        this.viewIncrease = viewIncrease;
    }

    public int getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(int voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatisticResp{");
        sb.append("date=").append(date);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", viewIncrease=").append(viewIncrease);
        sb.append(", voteIncrease=").append(voteIncrease);
        sb.append('}');
        return sb.toString();
    }
}
