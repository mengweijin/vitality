package com.github.mengweijin.quickboot.framework.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * 在Java编写应用时，有时需要在程序中调用另一个线程的可执行程序或系统命令。
 * Process ps = Runtime.getRuntime().exec("p.exe");
 * ps.waitfor();
 * <p>
 * Runtime.getRuntime()返回当前应用程序的Runtime对象，该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，
 * 并返回与该子进程对应的Process对象实例。通过Process可以控制该子进程的执行或获取该子进程的信息。
 * 它的所有标准io(即stdin，stdout，stderr)操作都将通过三个流(getOutputStream()，getInputStream()，getErrorStream())重定向到父进程。
 * 父进程使用这些流来提供到子进程的输入和获得从子进程的输出。因为有些本机平台仅针对标准输入和输出流提供有限的缓冲区大小，如果读
 * 写子进程的输出流或输入流迅速出现失败，则可能导致子进程阻塞，甚至产生死锁。
 * ps.waitfor()的目的是等待子进程完成再往下执行。
 * <p>
 * 也就是说：如果程序不断在向标准输出流和标准错误流写数据，而JVM不读取的话，当缓冲区满之后将无法继续写入数据，最终造成阻塞在waifor()这里。
 * <p>
 * 执行一个有标准输出的DOS可执行程序在windows平台上，运行被调用程序的DOS窗口执行完毕后往往并不会自动关闭，从而导致Java应用
 * 程序阻塞在waitfor()。导致该现象的一个可能的原因是，该可执行程序的标准输出比较多，而运行窗口的标准输出缓冲区不够大。
 * 解决的办法是，利用Java提供的Process类提供的方法让Java虚拟机截获被调用程序的DOS运行窗口的标准输出，在waitfor()命令之前读出
 * 窗口的标准输出缓冲区中的内容。
 * <p>
 * InputStream getErrorStream()：获得子进程的错误流
 * InputStream getInputStream()：获得子进程的输入流
 * 可以考虑使用两个线程来同时清空process获取的两个输入流。
 * <p>
 * 创建线程方法之一：实现Runnable接口
 * <p>
 * 步骤：
 * 1.定义类实现Runnable接口
 * 2.重写run()方法,这个方法里面实现的是实际要做的操作。
 * 3.定义一个方法，在里面实现该类的线程，同时调用线程的start()方法
 * 例：public void start(){
 * Thread thread = new Thread(this);
 * thread.setDaemon(true);设置成守护线程
 * thread.start();
 * }
 * <p>
 * 守护线程与非守护线程：
 * Java分为两种线程：用户线程和守护线程
 * <p>
 * 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
 * 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 * 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：
 * (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 * (2) 在Daemon线程中产生的新线程也是Daemon的。
 * (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 * <p>
 * 守护线程与非守护线程：
 * Java分为两种线程：用户线程和守护线程
 * <p>
 * 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
 * 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 * 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：
 * (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 * (2) 在Daemon线程中产生的新线程也是Daemon的。
 * (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 * <p>
 * 守护线程与非守护线程：
 * Java分为两种线程：用户线程和守护线程
 * <p>
 * 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
 * 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 * 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：
 * (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 * (2) 在Daemon线程中产生的新线程也是Daemon的。
 * (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 * <p>
 * 守护线程与非守护线程：
 * Java分为两种线程：用户线程和守护线程
 * <p>
 * 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
 * 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 * 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：
 * (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 * (2) 在Daemon线程中产生的新线程也是Daemon的。
 * (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 */


/**
 守护线程与非守护线程：
 Java分为两种线程：用户线程和守护线程

 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：
 (1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
 (2) 在Daemon线程中产生的新线程也是Daemon的。
 (3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。
 */

/**
 * @author Meng WEi Jin
 * @description
 **/
@Slf4j
public class ProcessUtils {
    /**
     * 执行命令
     * @param command 命令行命令
     */
    public static void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            // 读取子线程输入流和错误流
            readCache(process);
            // 阻塞，等待process的子进程执行完毕后继续执行
            int status = process.waitFor();
            if(status != 0) {
                throw new RuntimeException("Execute command failed! command => " + command);
            }
        } catch (IOException | InterruptedException | RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void readCache(Process process) {
        // 读取子进程的输入流
        Thread inputDaemonThread = new ProcessInputDaemonThread(process.getInputStream());
        // 设置成守护线程
        inputDaemonThread.setDaemon(true);
        inputDaemonThread.start();

        // 读取子进程的错误流
        Thread errorDaemonThread = new ProcessInputDaemonThread(process.getErrorStream());
        // 设置成守护线程
        errorDaemonThread.setDaemon(true);
        errorDaemonThread.start();
    }

    @AllArgsConstructor
    static class ProcessInputDaemonThread extends Thread {

        private InputStream inputStream;

        @Override
        public void run() {
            try (
                    InputStreamReader inReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(inReader)
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    log.debug(line);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
