package datastruct;

public class OneLink<T> {

	private Node<T> tail;
	private Node<T> head;

	public OneLink() {
		head = tail = null;
	}

	private static class Node<T> {
		T data;
		Node<T> next;

		public Node(T data, Node<T> next) {
			super();
			this.data = data;
			this.next = next;
		}

		public Node(T data) {
			super();
			this.data = data;
			this.next = null;
		}

	}

	// 为空链表增加头结点
	public void addHead(T point) {
		head = new Node<T>(point);
		if (tail == null)
			tail = head;
	}

	// 为链表增加尾节点
	public void addTail(T point) {
		tail = new Node<T>(point);
		head.next = tail;
	}

	public boolean insert(T point) throws Exception {
		Node<T> preNext = head.next;
		Node<T> newNode = new Node<T>(point);
		if (preNext != null) {
			head.next = newNode;
			newNode.next = preNext;
		} else {
			throw new Exception("请创建完整列表");
		}
		return true;
	}

	public void delete(T data) { // 删除某一节点
		Node<T> curr = head, prev = null;
		boolean b = false;
		while (curr != null) {
			if (curr.data.equals(data)) {
				// 判断是什么节点
				if (curr == head) { // 如果删除的是头节点
					System.out.println('\n' + "delete head node");
					head = curr.next;
					b = true;
					return;
				} else if (curr == tail) { // 如果删除的是尾节点
					System.out.println('\n' + "delete tail node");

					tail = prev;
					prev.next = null;
					b = true;
					return;
				} else { // 如果删除的是中间节点（即非头节点或非尾节点）
					System.out.println('\n' + "delete center node");
					prev.next = curr.next;
					b = true;
					return;
				}
			}
			prev = curr;
			curr = curr.next;
		}
		if (b == false) {
			System.out.println('\n' + "没有这个数据");
		}
	}

	public void show() { // 打印链表
		Node<T> curr = head;
		if (isEmpty()) {
			System.out.println("链表错误");
			return;
		} else {
			while (curr != null) {
				System.out.print(curr.data + " ");
				curr = curr.next;
			}
		}
	}

	public boolean isEmpty() { // 判断链表是否为空
		if (head != null && tail != null) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		OneLink<Integer> mylist = new OneLink<Integer>();// 构造一个空链表
		mylist.addHead(5);
		mylist.addTail(6);
		mylist.insert(7);
		mylist.insert(3);
		mylist.show();
		mylist.delete(1);
		mylist.show();
		mylist.delete(5);
		mylist.show();
		mylist.delete(6);
	}
}
