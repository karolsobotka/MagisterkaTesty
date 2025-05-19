using NUnit.Framework;
using System;

namespace UnitTest
{
    [TestFixture]
    public class CalculatorTest
    {
        private Calculator calculator;

        [SetUp]
        public void Setup()
        {
            calculator = new Calculator();
        }

        [Test]
        public void TestAddition()
        {
            Assert.That(calculator.Add(2, 3), Is.EqualTo(5));
        }

        [Test]
        public void TestSubtraction()
        {
            Assert.That(calculator.Subtract(10, 4), Is.EqualTo(6));
        }

        [Test]
        public void TestMultiplication()
        {
            Assert.That(calculator.Multiply(3, 4), Is.EqualTo(12));
        }

        [Test]
        public void TestDivision()
        {
            Assert.That(calculator.Divide(10, 2), Is.EqualTo(5));
        }

        [Test]
        public void TestDivisionByZero()
        {
            var ex = Assert.Throws<ArgumentException>(() => calculator.Divide(5, 0));
            Assert.That(ex.Message, Is.EqualTo("Cannot divide by zero"));
        }
    }
}
