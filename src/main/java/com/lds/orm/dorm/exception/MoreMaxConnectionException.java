package com.lds.orm.dorm.exception;

/**
 * Title: MoreMaxConnectionException
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/11
 */
public class MoreMaxConnectionException extends RuntimeException {

    public MoreMaxConnectionException(){
        super("more then maxConnectionNum");
    }


    public static void main(String[] a){

    }
}
