package com.ldf.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;


/**
 * Created by ldf on 2018/6/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private int age;
    private Date birth;
    private Boolean sex;
}
