package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorTest {

    @Test
    fun addTest(){
        // given
        val calculator =  Calculator(5)
        // when
        calculator.add(3)
        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest(){
        // given
        val calculator = Calculator(5)
        // when
        calculator.minus(3)
        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest(){
        // given
        val calculator = Calculator(5)
        // when
        calculator.multiply(2)
        // then
        assertThat(calculator.number).isEqualTo(10)
    }

    @Test
    fun divideTest(){
        //given
        val calculator = Calculator(6)
        // when
        calculator.divide(2)
        // then
        assertThat(calculator.number).isEqualTo(3)
    }

    @Test
    fun divideExceptionTest(){
        // given
        val calculator = Calculator(6)
        val message = assertThrows<IllegalArgumentException> {
            // when
            calculator.divide(0)
            // then
        }.message
        assertThat(message).isEqualTo("0으로 나눌 수 업습니다.")
    }
}