export type TreeNode<E> = {
  element: E
  left?: TreeNode<E>
  right?: TreeNode<E>
}

export type Consumer<E> = (e: E) => void
export type Traversal<E> = (root: TreeNode<E>, consumer: Consumer<E>) => void

export function preorder<E>(root: TreeNode<E>, consumer: Consumer<E>): void {
  consumer(root.element)
  root.left && preorder(root.left, consumer)
  root.right && preorder(root.right, consumer)
}

export function inorder<E>(root: TreeNode<E>, consumer: Consumer<E>): void {
  root.left && inorder(root.left, consumer)
  consumer(root.element)
  root.right && inorder(root.right, consumer)
}

export function postorder<E>(root: TreeNode<E>, consumer: Consumer<E>): void {
  root.left && postorder(root.left, consumer)
  root.right && postorder(root.right, consumer)
  consumer(root.element)
}

export function levelorder<E>(root: TreeNode<E>, consumer: Consumer<E>): void {
  const queue = Array<TreeNode<E>>()
  queue.push(root)
  while (queue.length > 0) {
    for (let i = 0; i < queue.length; i++) {
      const node = queue.shift() as TreeNode<E>
      consumer(node.element)
      node.left && queue.push(node.left)
      node.right && queue.push(node.right)
    }
  }
}

export function toList<E>(root: TreeNode<E>, traverser: Traversal<E>): E[] {
  const list = Array<E>()
  traverser(root, e => list.push(e))
  return list
}
