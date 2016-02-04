/*
 * SonarQube Java
 * Copyright (C) 2012-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.se.constraint;

public class BooleanConstraint implements Constraint {
  private static final BooleanConstraint TRUE = new BooleanConstraint();
  private static final BooleanConstraint FALSE = new BooleanConstraint();

  public static BooleanConstraint trueConstraint() {
    return TRUE;
  }
  public static BooleanConstraint falseConstraint() {
    return FALSE;
  }

  @Override
  public boolean isNull() {
    return false;
  }

  public boolean isTrue() {
    return this == TRUE;
  }

  public boolean isFalse() {
    return this == FALSE;
  }

  public BooleanConstraint inverse() {
    if (TRUE == this) {
      return FALSE;
    }
    return TRUE;
  }

  @Override
  public String toString() {
    if(this == TRUE) {
      return "TRUE";
    }
    return "FALSE";
  }
}
