import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

/**
 * 注解：
 * @BeforeEach：在每个单元测试方法执行前都执行一遍
 * @BeforeAll：在每个单元测试方法执行前执行一遍（只执行一次）
 * @DisplayName("商品入库测试")：用于指定单元测试的名称
 * @Disabled：当前单元测试置为无效，即单元测试时跳过该测试
 * @RepeatedTest(n)：重复性测试，即执行n次
 * @ParameterizedTest：参数化测试，
 * @ValueSource(ints = {1, 2, 3})：参数化测试提供数据
 *
 * 断言：
 * org.junit.jupiter.api.Assertions
 */
@DisplayName("Junit5单元测试")
public class Junit5Test {

    /**
     * 在每个单元测试方法执行前都执行一遍
     */
    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @DisplayName("测试断言")
    void testEquals() {
        // assertTrue与assertFalse用来判断条件是否为true或false
        Assertions.assertTrue(3 < 4);
        Assertions.assertFalse( 3 > 4);

        // assertNull与assertNotNull用来判断条件是否为·null
        Assertions.assertNull(null);
        Assertions.assertNotNull(new Object());
    }

    /**
     * assertThrows 用来判断执行抛出的异常是否符合预期，并可以使用异常类型接收返回值进行其他操作
     */
    @Test
    public void assertThrows() {
        ArithmeticException arithExcep = Assertions.assertThrows(ArithmeticException.class, () -> {
            int m = 5/0;
        });
        Assertions.assertEquals("/ by zero", arithExcep.getMessage());
    }

    /**
     * assertTimeout用来判断执行过程是否超时
     */
    @Test
    public void assertTimeout() {
        String actualResult = Assertions.assertTimeout(Duration.ofSeconds(2), () -> {
            Thread.sleep(1000);
            return "result";
        });
        Assertions.assertEquals("result", actualResult);
    }

    /**
     * assertAll是组合断言，当它内部所有断言正确执行完才算通过
     */
    @Test
    public void assertAll() {
        Assertions.assertAll("测试item商品下单",
                () -> {
                    //模拟用户余额扣减
                    Assertions.assertTrue(1 < 2, "余额不足");
                },
                () -> {
                    //模拟item数据库扣减库存
                    Assertions.assertTrue(3 < 4);
                },
                () -> {
                    //模拟交易流水落库
                    Assertions.assertNotNull(new Object());
                }
        );
    }

    /**
     * 重复性测试
     * 在许多场景中我们需要对同一个接口方法进行重复测试，例如对幂等性接口的测试。
     * JUnit Jupiter通过使用@RepeatedTest(n)指定需要重复的次数
     */
    @RepeatedTest(3)
    @DisplayName("重复测试")
    public void repeatedTest() {
        System.out.println("调用");
    }

    /**
     * 参数化测试
     * 参数化测试可以按照多个参数分别运行多次单元测试这里有点类似于重复性测试，只不过每次运行传入的参数不同。
     * 需要使用到@ParameterizedTest，同时也需要@ValueSource提供一组数据，它支持八种基本类型以及String和自定义对象类型，使用极其方便。
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("参数化测试")
    public void paramTest(int a) {
        Assertions.assertTrue(a > 0 && a < 4);
    }

    /**
     * 内嵌测试
     * JUnit5提供了嵌套单元测试的功能，可以更好展示测试类之间的业务逻辑关系，我们通常是一个业务对应一个测试类，有业务关系的类其实可以写在一起。
     * 这样有利于进行测试。而且内联的写法可以大大减少不必要的类，精简项目，防止类爆炸等一系列问题。
     */
    @Nested
    @DisplayName("内嵌订单测试")
    class OrderTestClass {
        @Test
        @DisplayName("取消订单")
        void cancelOrder() {
            int status = -1;
            System.out.println("取消订单成功,订单状态为:"+status);
        }
    }
}
