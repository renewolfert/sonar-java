class A {
  public void assign(boolean a, boolean b) {
    a = b;
    if (a != b) { // Noncompliant {{Change this condition so that it does not always evaluate to "false"}}
    }
  }
}

