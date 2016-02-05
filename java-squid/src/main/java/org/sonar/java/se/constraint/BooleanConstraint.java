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

import org.picocontainer.annotations.Nullable;
import org.sonar.plugins.java.api.tree.Tree;

public abstract class BooleanConstraint implements Constraint {
  protected final Tree origin;
  private final boolean isTrue;

  public BooleanConstraint(boolean isTrue, @Nullable Tree origin) {
    this.isTrue = isTrue;
    this.origin = origin;
  }

  public static BooleanConstraint trueConstraint(@Nullable Tree condition) {
    return new TrueBooleanConstraint(condition);
  }

  public static BooleanConstraint falseConstraint(@Nullable Tree condition) {
    return new FalseBooleanConstraint(condition);
  }

  @Override
  public boolean isNull() {
    return false;
  }

  public boolean isTrue() {
    return isTrue;
  }

  public boolean isFalse() {
    return !isTrue;
  }

  public abstract BooleanConstraint inverse();

  public boolean sameAs(BooleanConstraint booleanConstraint) {
    return isTrue == booleanConstraint.isTrue;
  }

  public Tree origin() {
    return origin;
  }

  private static class TrueBooleanConstraint extends BooleanConstraint {
    public TrueBooleanConstraint(Tree origin) {
      super(true, origin);
    }

    @Override
    public BooleanConstraint inverse() {
      return new FalseBooleanConstraint(this.origin);
    }

    @Override
    public String toString() {
      return "TRUE";
    }
  }

  private static class FalseBooleanConstraint extends BooleanConstraint {

    public FalseBooleanConstraint(@Nullable Tree origin) {
      super(false, origin);
    }

    @Override
    public BooleanConstraint inverse() {
      return new TrueBooleanConstraint(this.origin);
    }

    @Override
    public String toString() {
      return "FALSE";
    }
  }
}
