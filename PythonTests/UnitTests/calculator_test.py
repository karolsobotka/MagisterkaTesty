import pytest
from calculator import Calculator

calculator = Calculator()

def test_addition():
    assert calculator.add(2, 3) == 5

def test_subtraction():
    assert calculator.subtract(10, 4) == 6

def test_multiplication():
    assert calculator.multiply(3, 4) == 12

def test_division():
    assert calculator.divide(10, 2) == 5

def test_division_by_zero():
    with pytest.raises(ValueError, match="Cannot divide by zero"):
        calculator.divide(5, 0)
