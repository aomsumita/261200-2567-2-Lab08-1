import java.util.Random;
public class Main {
    public static void main(String[] args) {

        NumberPrinter thread1 = new NumberPrinter(1);
        NumberPrinter thread2 = new NumberPrinter(2);
        NumberPrinter thread3 = new NumberPrinter(3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

class NumberPrinter extends Thread {
    private int threadNumber;

    public NumberPrinter(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 1; i <= 50; i++) {
            try {
                Thread.sleep(random.nextInt(91) + 10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("thread #" + threadNumber + " : " + i);
        }
    }
}
/* Before adding Thread.sleep() : ก่อนจะมีโปรแกรมจะทำงานเร็วมาก ไม่มีการหยุดระหว่างการทำงานเลย ผลลัพธ์ที่ได้ จะเป็นแบบสุ่มๆไม่เหมือนกันในแต่ละครั้ง เช่น 50 1 2 หรือ
                                  thread #1 : 46 thread #1 : 47 thread #1 : 48 thread #1 : 49 thread #1 : 50 thread #3 : 48
                                  จะเห็นได้ว่าthread 1 มีการทำงานต่อๆกันเยอะ คือครองผลที่จากcpuเยอะ ทำให้ผลลัพธ์ที่ได้ส่วนใหญ่มาจากthread1 ก่อน แล้วค่อยมีthread ตามมา (คือไม่เป็น thread #1 thread #2 thread #3 สลับกันไป)
   After adding Thread.sleep() : โปรแกรมจะมีการทำงานสลับกันมากขึ้นและมีการหยุดพัก ทำให้threadใดthreadหนึ่งไม่ครอง cpu นานเกินไป ตัวอย่างเช่น thread #1 : 18 thread #3 : 18 thread #1 : 19 thread #3 : 19 thread #2 : 16
                                 จะเห็นได้ว่ามีการสลับกันทำงานของแต่ละเทรดมากขึ้น
   Multithreading works in Java : คือการรันหลายๆthreadพร้อมกัน โดยแต่ละthreadจะทำงานแยกออกจากกัน(ทำใครทำมัน)
 */