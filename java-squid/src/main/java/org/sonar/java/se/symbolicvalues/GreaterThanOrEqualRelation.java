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
package org.sonar.java.se.symbolicvalues;

import com.google.common.base.Preconditions;
import org.sonar.plugins.java.api.tree.Tree;

import javax.annotation.CheckForNull;
import java.util.List;

public class GreaterThanOrEqualRelation extends BinaryRelation {

  GreaterThanOrEqualRelation(SymbolicValue v1, SymbolicValue v2, List<Tree> expressions) {
    super(RelationalSymbolicValue.Kind.GREATER_THAN_OR_EQUAL, v1, v2, expressions);
  }

  @Override
  protected BinaryRelation symmetric() {
    return new LessThanOrEqualRelation(rightOp, leftOp, expressions);
  }

  @Override
  public BinaryRelation inverse() {
    return new LessThanRelation(leftOp, rightOp, expressions);
  }

  @Override
  protected RelationState isImpliedBy(BinaryRelation relation) {
    return relation.impliesGreaterThanOrEqual();
  }

  @Override
  protected RelationState impliesEqual() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected RelationState impliesNotEqual() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected RelationState impliesMethodEquals() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected RelationState impliesNotMethodEquals() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected RelationState impliesGreaterThan() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected RelationState impliesGreaterThanOrEqual() {
    return RelationState.FULFILLED;
  }

  @Override
  protected RelationState impliesLessThan() {
    return RelationState.UNFULFILLED;
  }

  @Override
  protected RelationState impliesLessThanOrEqual() {
    return RelationState.UNDETERMINED;
  }

  @Override
  protected BinaryRelation combinedAfter(BinaryRelation relation) {
    return relation.combinedWithGreaterThanOrEqual(this);
  }

  @Override
  protected BinaryRelation combinedWithEqual(EqualRelation relation) {
    return new GreaterThanOrEqualRelation(leftOp, relation.rightOp, expressions);
  }

  @Override
  @CheckForNull
  protected BinaryRelation combinedWithNotEqual(NotEqualRelation relation) {
    return null;
  }

  @Override
  @CheckForNull
  protected BinaryRelation combinedWithMethodEquals(MethodEqualsRelation relation) {
    return new GreaterThanOrEqualRelation(leftOp, relation.rightOp, expressions);
  }

  @Override
  protected BinaryRelation combinedWithNotMethodEquals(NotMethodEqualsRelation relation) {
    return null;
  }

  @Override
  protected BinaryRelation combinedWithGreaterThan(GreaterThanRelation relation) {
    return new GreaterThanRelation(leftOp, relation.rightOp, expressions);
  }

  @Override
  protected BinaryRelation combinedWithGreaterThanOrEqual(GreaterThanOrEqualRelation relation) {
    return new GreaterThanOrEqualRelation(leftOp, relation.rightOp, expressions);
  }

  @Override
  protected BinaryRelation combinedWithLessThan(LessThanRelation relation) {
    return null;
  }

  @Override
  protected BinaryRelation combinedWithLessThanOrEqual(LessThanOrEqualRelation relation) {
    return null;
  }

  @Override
  @CheckForNull
  protected BinaryRelation conjunction(BinaryRelation relation) {
    Preconditions.checkArgument(leftOp.equals(relation.leftOp) && rightOp.equals(relation.rightOp), "Conjunction condition not matched!");
    switch (relation.kind) {
      case NOT_EQUAL:
      case NOT_METHOD_EQUALS:
        return new GreaterThanRelation(leftOp, rightOp, expressions);
      case LESS_THAN_OR_EQUAL:
        return new EqualRelation(leftOp, rightOp, expressions);
      default:
        return super.conjunction(relation);
    }
  }
}
