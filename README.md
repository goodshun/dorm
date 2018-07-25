# dorm
数据库orm框架

连接池源码介绍：

1.连接池功能主要包含连接的创建，获取，释放，定时清理，慢sql监控等功能以及可配置化池大小，最小连接数，最大连接数，最大空闲时间，等待时间等等</br>

2.自定义connection接口实现，进行封装赋予对应的属性-连接相关生命周期信息

3.连接的获取采用ThreadLocal保存，保证同一线程使用同一连接，保证事务

4.使用定时任务，定时清理空闲连接，长时间未释放的连接

5.使用锁保证连接池并发安全，同时使用 可重入锁条件唤醒机制 ，将连接的释放和获取进行衔接

