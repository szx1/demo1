package generator.domain;

import com.example.demo.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @TableName test
 */
@Setter
@Getter
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class Test implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer age;

    /**
     *
     */
    @Column(name = "for_index")
    private Integer forIndex;

    @Column(name = "gmt_modify")
    private Date date;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "note")
    private String note;

    private static final long serialVersionUID = 1L;

    public Test(String name, Integer age, Integer forIndex, Sex sex) {
        this.name = name;
        this.age = age;
        this.forIndex = forIndex;
        this.date = new Date();
        this.sex = sex;
    }

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
        Test other = (Test) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
                && (this.getForIndex() == null ? other.getForIndex() == null : this.getForIndex().equals(other.getForIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getForIndex() == null) ? 0 : getForIndex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", age=").append(age);
        sb.append(", forIndex=").append(forIndex);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}