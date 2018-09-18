package com.lds.orm.dorm.test;

import com.lds.orm.dorm.annotation.PrimaryKey;
import com.lds.orm.dorm.annotation.Table;
import com.lds.orm.dorm.model.Model;

/**
 * Title: User
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/09/18
 */

@Table(tableName = "T_USER") //自动建表配置，如不需要自动建表可去掉
public class User extends Model<User> {
    @PrimaryKey
    private String userName;
    private Integer age;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }


    public static void main(String[] args) {
        User user = new User();
        user.setUserName("张三");
        user.setAge(18);
        user.save();
    }
}
