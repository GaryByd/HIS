package domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName ai_model
 */
@TableName(value ="ai_model")
@Data
public class AiModel implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模型版本
     */
    @TableField(value = "version")
    private String version;

    /**
     * 部署时间
     */
    @TableField(value = "deploy_time")
    private Date deploy_time;

    /**
     * 准确率
     */
    @TableField(value = "accuracy")
    private String accuracy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AiModel other = (AiModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getDeploy_time() == null ? other.getDeploy_time() == null : this.getDeploy_time().equals(other.getDeploy_time()))
            && (this.getAccuracy() == null ? other.getAccuracy() == null : this.getAccuracy().equals(other.getAccuracy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getDeploy_time() == null) ? 0 : getDeploy_time().hashCode());
        result = prime * result + ((getAccuracy() == null) ? 0 : getAccuracy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", version=").append(version);
        sb.append(", deploy_time=").append(deploy_time);
        sb.append(", accuracy=").append(accuracy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}