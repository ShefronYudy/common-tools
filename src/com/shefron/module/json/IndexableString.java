package com.shefron.module.json;

class IndexableString {
	private StringBuilder src;
	private int indexNum;

	IndexableString() {
		this(0, null);
	}

	IndexableString(String str) {
		this(0, str);
	}

	IndexableString(int index, String str) {
		this.src = new StringBuilder(str);
		this.indexNum = index;
	}

	StringBuilder source() {
		return this.src;
	}

	int moveIndex() {
		return moveIndex(1);
	}

	int moveIndex(int increment) {
		return this.indexNum += increment;
	}

	int moveAndTrimStart() {
		return moveAndTrimStart(1);
	}

	int moveAndTrimStart(int increment) {
		this.indexNum += increment;
		trimStart();
		return this.indexNum;
	}

	int findNearestIndex(char... chs) {
		int i = this.indexNum;
		while (i < this.src.length()) {
			for (char ch : chs) {
				if (ch == this.src.charAt(i)) {
					return i - this.indexNum;
				}
			}
			i++;
		}
		return -1;
	}

	int indexOf(String str) {
		int idx = this.src.indexOf(str, this.indexNum) - this.indexNum;
		if (idx < 0) {
			idx = -1;
		}
		return idx;
	}

	boolean startsWith(String str) {
		return str.length() <= length() ? valueBy(str.length()).equals(str) : false;
	}

	int currentIndex() {
		return this.indexNum;
	}

	public int length() {
		return this.src.length() - this.indexNum;
	}

	public boolean moreChars() {
		return this.src.length() > this.indexNum;
	}

	char currentChar() {
		return this.src.charAt(this.indexNum);
	}

	char charAt(int index) {
		return this.src.charAt(this.indexNum + index);
	}

	public boolean charEquals(int index, char ch) {
		return this.src.charAt(this.indexNum + index) == ch;
	}

	public String valueBy(int end) {
		return this.src.substring(this.indexNum, this.indexNum + end);
	}

	public String valueBy(int space, int end) {
		return this.src.substring(this.indexNum + space, this.indexNum + end);
	}

	private static int TRIMABLE_CHAR = 33;

	IndexableString trimStart() {
		if (currentChar() < TRIMABLE_CHAR) {
			this.indexNum += 1;
			trimStart();
		}
		return this;
	}

	void clear() {
		this.src.setLength(0);
		this.src = null;
		this.indexNum = 0;
	}

	public String toString() {
		return "IndexableString [sb=" + this.src + ", indexNum=" + this.indexNum + "]";
	}
}
