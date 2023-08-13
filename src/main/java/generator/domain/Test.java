package generator.domain;

import com.example.demo.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @TableName test
 */
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

    private static final long serialVersionUID = 1L;

    public Test(String name, Integer age, Integer forIndex, Sex sex) {
        this.name = name;
        this.age = age;
        this.forIndex = forIndex;
        this.date = new Date();
        this.sex = sex;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public Integer getAge() {
        return age;
    }

    /**
     *
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     *
     */
    public Integer getForIndex() {
        return forIndex;
    }

    /**
     *
     */
    public void setForIndex(Integer forIndex) {
        this.forIndex = forIndex;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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