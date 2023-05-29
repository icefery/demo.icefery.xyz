# 【LeetCode】初级算法

## 数组

### [删除排序数组中的重复项](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/)

```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }
    int fast = 1;
    int slow = 1;
    while (fast < nums.length) {
        if (nums[fast] != nums[fast - 1]) {
            nums[slow] = nums[fast];
            slow++;
        }
        fast++;
    }
    return slow;
}
```

### [买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)

```java
public int maxProfit(int[] prices) {
    int ans = 0;
    int n = prices.length;
    for (int i = 1; i < n; i++) {
        ans += Math.max(0, prices[i] - prices[i - 1]);
    }
    return ans;
}
```

### [旋转数组](https://leetcode-cn.com/problems/rotate-array/)

```java
public void rotate(int[] nums, int k) {
    k %= nums.length;
    // [0, n-1]
    for (int i=0, j=nums.length-1; i < j; i++, j--) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    // [0, k-1]
    for (int i=0, j=k-1; i < j; i++, j--) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    // [k, n-1]
    for (int i=k, j=nums.length-1; i < j; i++, j--) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

### [存在重复元素](https://leetcode-cn.com/problems/contains-duplicate/)

```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int x : nums) {
        if (!set.add(x)) {
            return true;
        }
    }
    return false;
}
```

### [只出现一次的数字](https://leetcode-cn.com/problems/single-number/)

- 任何数和 0 做异或运算，结果仍然是原来的数
- 任何数和其自身做异或运算，结果是 0
- 异或运算满足交换律和结合律

```java
public int singleNumber(int[] nums) {
    int single = 0;
    for (int num : nums) {
        single ^= num;
    }
    return single;
}
```

### [两个数组的交集 II](https://leetcode-cn.com/problems/intersection-of-two-arrays-ii/)

```java
public int[] intersect(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return intersect(nums2, nums1);
    }
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums1) {
        int count = map.getOrDefault(num, 0) + 1;
        map.put(num, count);
    }
    int[] intersection = new int[nums1.length];
    int index = 0;
    for (int num : nums2) {
        int count = map.getOrDefault(num, 0);
        if (count > 0) {
            intersection[index] = num;
            count--;
            index++;
            if (count > 0) {
                map.put(num, count);
            } else {
                map.remove(num);
            }
        }
    }
    return Arrays.copyOfRange(intersection, 0, index);
}
```

### [加一](https://leetcode-cn.com/problems/plus-one/)

```java
public int[] plusOne(int[] digits) {
    for (int i = digits.length - 1; i >= 0; i--) {
        if (digits[i] != 9) {
            digits[i]++;
            for (int j = i + 1; j < digits.length; j++) {
                digits[j] = 0;
            }
            return digits;
        }
    }
    int[] ans = new int[digits.length + 1];
    ans[0] = 1;
    return ans;
}
```

### [移动零](https://leetcode-cn.com/problems/move-zeroes/)

```java
public void moveZeroes(int[] nums) {
    int left = 0;
    int right = 0;
    while (right < nums.length) {
        if (nums[right] != 0) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
        }
        right++;
    }
}
```

### [两数之和](https://leetcode-cn.com/problems/two-sum/)

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        if (map.containsKey(target - nums[i])) {
            return new int[]{map.get(target - nums[i]), i};
        }
        map.put(nums[i], i);
    }
    return new int[0];
}
```

### [有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

```java
public boolean isValidSudoku(char[][] board) {
    int[][] rows = new int[9][9];
    int[][] cols = new int[9][9];
    int[][][] subboxes = new int[3][3][9];
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            char c = board[i][j];
            if (c != '.') {
                int index = c - '0' - 1;
                rows[i][index]++;
                cols[j][index]++;
                subboxes[i / 3][j / 3][index]++;
                if (rows[i][index] > 1 || cols[j][index] > 1 || subboxes[i / 3][j / 3][index] > 1) {
                    return false;
                }
            }
        }
    }
    return true;
}
```

### [旋转图像](https://leetcode-cn.com/problems/rotate-image/)

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n / 2; i++) {
        for (int j = 0; j < (n+1) / 2; j++) {
            int temp             = matrix[i][j];
            matrix[i][j]         = matrix[n-j-1][i];
            matrix[n-j-1][i]     = matrix[n-i-1][n-j-1];
            matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
            matrix[j][n-i-1]     = temp;
        }
    }
}
```

## 字符串

### [反转字符串](https://leetcode-cn.com/problems/reverse-string/)

```java
public void reverseString(char[] s) {
    for (int left=0, right=s.length-1; left < right; left++, right--) {
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
    }
}
```

### [整数反转](https://leetcode-cn.com/problems/reverse-integer/)

```java
public int reverse(int x) {
    int rev = 0;
    while (x != 0) {
        if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
            return 0;
        }
        int digit = x % 10;
        x /= 10;
        rev = rev * 10 + digit;
    }
    return rev;
}
```

### [字符串中的第一个唯一字符](https://leetcode-cn.com/problems/first-unique-character-in-a-string/)

```java
public int firstUniqChar(String s) {
    Map<Character, Integer> position = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (position.containsKey(ch)) {
            position.put(ch, -1);
        } else {
            position.put(ch, i);
        }
    }
    int first = s.length();
    for (Map.Entry<Character, Integer> entry : position.entrySet()) {
        int pos = entry.getValue();
        if (pos != -1 && pos < first) {
            first = pos;
        }
    }
    if (first == s.length()) {
        first = -1;
    }
    return first;
}
```

### [有效的字母异位词](https://leetcode-cn.com/problems/valid-anagram/)

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        map.put(ch, map.getOrDefault(ch, 0) + 1);
    }
    for (int i = 0; i < t.length(); i++) {
        char ch = t.charAt(i);
        map.put(ch, map.getOrDefault(ch, 0) - 1);
        if (map.get(ch) < 0) {
            return false;
        }
    }
    return true;
}
```

### [验证回文串](https://leetcode-cn.com/problems/valid-palindrome/)

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }
        if (left < right) {
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
    }
    return true;
}
```

### 字符串转换整数

### 实现`strStr()`

### 外观数列

### [最长公共前缀](https://leetcode-cn.com/problems/longest-common-prefix/)

![](https://assets.leetcode-cn.com/solution-static/14/14_fig2.png)

```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) {
        return "";
    }
    int count = strs.length;
    int length = strs[0].length();
    for (int i = 0; i < length; i++) {
        char c = strs[0].charAt(i);
        for (int j = 1; j < count; j++) {
            if (i == strs[j].length() || strs[j].charAt(i) != c) {
                return strs[0].substring(0, i);
            }
        }
    }
    return strs[0];
}
```

## 链表

### [删除链表中的节点](https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

```java
public ListNode deleteNode(ListNode head, int val) {
    ListNode dummy = new ListNode(0, head);
    ListNode p = dummy;
    while (p.next.val != val) {
        p = p.next;
    }
    p.next = p.next.next;
    return dummy.next;
}
```

### [删除链表的倒数第 N 个节点](https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    Deque<ListNode> stack = new LinkedList<>();
    ListNode curr = dummy;
    while (curr != null) {
        stack.push(curr);
        curr = curr.next;
    }
    for (int i = 0; i < n; i++) {
        stack.pop();
    }
    ListNode prev = stack.peek();
    prev.next = prev.next.next;
    return dummy.next;
}
```

### [反转链表](https://leetcode-cn.com/problems/reverse-linked-list/)

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

### [合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode head = new ListNode(0);
    ListNode prev = head;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            prev.next = l1;
            l1 = l1.next;
        } else {
            prev.next = l2;
            l2 = l2.next;
        }
        prev = prev.next;
    }
    prev.next = l1 == null ? l2 : l1;
    return head.next;
}
```

### [回文链表](https://leetcode-cn.com/problems/palindrome-linked-list/)

```java

```

### [环形链表](https://leetcode-cn.com/problems/linked-list-cycle/)

```java
public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) {
        return false;
    }
    ListNode slow = head;
    ListNode fast = head.next;
    while (slow != fast) {
        if (fast == null || fast.next == null) {
            return false;
        }
        slow = slow.next;
        fast = fast.next.next;
    }
    return true;
}
```

## 树

### [二叉树的最大深度](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/)

> 如果我们知道了左子树和右子树的最大深度 `l` 和 `r`，那么该二叉树的最大深度即为 `max(l,r)+1`。

```java
public int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    } else {
        int l = maxDepth(root.left);
        int r = maxDepth(root.right);
        return Math.max(l, r) + 1;
    }
}
```

### [验证二叉搜索树](https://leetcode-cn.com/problems/validate-binary-search-tree/)

```java
public boolean isValidBST(TreeNode root) {
    Deque<TreeNode> stack = new LinkedList<>();
    double inorder = -Double.MAX_VALUE;
    while (!stack.isEmpty() || root != null) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        root = stack.pop();
        if (root.val <= inorder) {
            return false;
        }
        inorder = root.val;
        root = root.right;
    }
    return true;
}
```

### [对称二叉树](https://leetcode-cn.com/problems/symmetric-tree/)

```java
public boolean isSymmetric(TreeNode root) {
    return check(root, root);
}
public boolean check(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
        return true;
    }
    if (p == null || q == null) {
        return false;
    }
    return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
}
```

### [二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ret = new ArrayList<>();
    if (root == null) {
        return ret;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        int currentLevelSize = queue.size();
        for (int i = 1; i <= currentLevelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        ret.add(level);
    }
    return ret;
}
```

### [将有序数组转化为二叉搜索树](https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/)

```java
public TreeNode sortedArrayToBST(int[] nums) {
    return helper(nums, 0, nums.length -1);
}
public TreeNode helper(int[] nums, int left, int right) {
    if (left > right) {
        return null;
    }
    int mid = (left + right) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    root.left = helper(nums, left, mid - 1);
    root.right = helper(nums, mid + 1, right);
    return root;
}
```

## 排序和搜索

### 合并两个有序数组

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1;
    int p2 = n - 1;
    int tail = m + n - 1;
    int curr;
    while (p1 >= 0 || p2 >= 0) {
        if (p1 == -1) {
            curr = nums2[p2--];
        } else if (p2 == -1) {
            curr = nums1[p1--];
        } else if (nums1[p1] > nums2[p2]) {
            curr = nums1[p1--];
        } else {
            curr = nums2[p2--];
        }
        nums1[tail--] = curr;
    }
}
```

### 第一个错误的版本

## 动态规划

### 爬楼梯

### 买卖股票的最佳时机

### 最大子序和

### 打家劫舍

## 设计问题

### [打乱数组](https://leetcode-cn.com/problems/shuffle-an-array/)

```java
class Solution {
    int[] nums;
    int[] original;
    public Solution(int[] nums) {
        this.nums = nums;
        this.original = new int[nums.length];
        System.arraycopy(nums, 0, original, 0, nums.length);
    }
    public int[] reset() {
        System.arraycopy(original, 0, nums, 0, nums.length);
        return nums;
    }
    public int[] shuffle() {
        Random random = new Random();
        for (int i = 0; i < nums.length; ++i) {
            int j = i + random.nextInt(nums.length - i);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }
}
```

### [最小栈](https://leetcode-cn.com/problems/min-stack/)

```java
class MinStack {
    Deque<Integer> xStack;
    Deque<Integer> minStack;
    public MinStack() {
        xStack = new LinkedList<Integer>();
        minStack = new LinkedList<Integer>();
        minStack.push(Integer.MAX_VALUE);
    }
    public void push(int x) {
        xStack.push(x);
        minStack.push(Math.min(minStack.peek(), x));
    }
    public void pop() {
        xStack.pop();
        minStack.pop();
    }
    public int top() {
        return xStack.peek();
    }
    public int getMin() {
        return minStack.peek();
    }
}
```

## 数学

### Fizz Buzz

```java
public List<String> fizzBuzz(int n) {
    List<String> answer = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
        String s = "";
        if (i % 3 == 0) {
            s += "Fizz";
        }
        if (i % 5 == 0) {
            s += "Buzz";
        }
        if (s.length() == 0) {
            s += i;
        }
        answer.add(s);
    }
    return answer;
}
```

### 计数质数

```java
public int countPrimes(int n) {
    int ans = 0;
    for (int i = 2; i < n; i++) {
        ans += isPrime(i) ? 1 : 0;
    }
    return ans;
}
public boolean isPrime(int x) {
    for (int i = 2; i * i <= x; i++) {
        if (x % i == 0) {
            return false;
        }
    }
    return true;
}
```

### 3 的幂

- 试除法

  ```java
  public boolean isPowerOfThree(int n) {
      while (n != 0 && n % 3 == 0) {
          n /= 3;
      }
      return n == 1;
  }
  ```

- 判断是否为最大 3 的幂的约数
  ```java
  public boolean isPowerOfThree(int n) {
      return n > 0 && (int)Math.pow(3, 19) % n == 0;
  }
  ```

### 罗马数字转整数

```java
public int romanToInt(String s) {
    Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};
    int ans = 0;
    for (int i = 0; i < s.length(); i++) {
        int value = symbolValues.get(s.charAt(i));
        if (i < s.length() - 1 && value < symbolValues.get(s.charAt(i + 1))) {
            ans -= value;
        } else {
            ans += value;
        }
    }
    return ans;
}
```

### 位 1 的个数

```java
public int hammingWeight(int n) {
    int ret = 0;
    while (n != 0) {
        n &= n - 1;
        ret++;
    }
    return ret;
}
```

## 其它

### 汉明距离

```java
public int hammingDistance(int x, int y) {
    int s = x ^ y;
    int ret = 0;
    while (s != 0) {
        s &= s - 1;
        ret++;
    }
    return ret;
}
```

### [颠倒二进制位](https://leetcode-cn.com/problems/reverse-bits/)

```java
private static final int M1 = 0x55555555; // 01010101010101010101010101010101
private static final int M2 = 0x33333333; // 00110011001100110011001100110011
private static final int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
private static final int M8 = 0x00ff00ff; // 00000000111111110000000011111111

public int reverseBits(int n) {
    n = n >>> 1 & M1 | (n & M1) << 1;
    n = n >>> 2 & M2 | (n & M2) << 2;
    n = n >>> 4 & M4 | (n & M4) << 4;
    n = n >>> 8 & M8 | (n & M8) << 8;
    return n >>> 16 | n << 16;
}
```

### [杨辉三角](https://leetcode-cn.com/problems/pascals-triangle)

![](https://pic.leetcode-cn.com/1626927345-DZmfxB-PascalTriangleAnimated2.gif)

```java
public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> ret = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
        List<Integer> row = new ArrayList<>();
        for (int j = 0; j <= i; j++) {
            if (j == 0 || j == i) {
                row.add(1);
            } else {
                row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
            }
        }
        ret.add(row);
    }
    return ret;
}
```

### [有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

```java
public boolean isValid(String a) {
    if (s.length() % 2 == 1) {
        return false;
    }
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
    Deque<Character> stack = new LinkedList<>();
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (pairs.containsKey(ch)) {
            if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                return false;
            }
            stack.pop();
        }else {
            stack.push(ch);
        }
    }
    return stack.isEmpty();
}
```

### 缺失数字
