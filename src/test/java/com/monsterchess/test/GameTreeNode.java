/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class GameTreeNode {

	public int getValue() {
		return value;
	}

	public boolean isTerminal() {
		return children == null;
	}

	public List<GameTreeNode> getChildren() {
		return children;
	}

	public List<String> getLegalMoves() {
		if (children == null)
			return new LinkedList<>();

		return children.stream()
				.map(node -> node.nodeName)
				.collect(Collectors.toList());
	}

	public TestPlayer getPlayer() {
		return player;
	}

	public GameTreeNode makeMove(String moveName) {
		if (children == null)
			throw new IllegalStateException("No legal move from terminal node " + nodeName);

		for (GameTreeNode child : children) {
			if (child.nodeName.equals(moveName))
				return child;
		}

		throw new IllegalArgumentException("No such move " + moveName + " from node " + nodeName);
	}

	public GameTreeNode(String nodeName, int value) {
		this.nodeName = nodeName;
		this.value = value;
	}

	public GameTreeNode(String nodeName, int value, TestPlayer player, GameTreeNode... children) {
		this.nodeName = nodeName;
		this.value = value;
		this.player = player;
		this.children = Collections.unmodifiableList(Arrays.asList(children));
	}

	private String nodeName;
	private int value;
	private TestPlayer player;
	private List<GameTreeNode> children;
}
