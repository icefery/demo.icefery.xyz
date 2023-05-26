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

export function inorder<E>(root: TreeNode<E>, consumer: Consumer<E>) {
  root.left && inorder(root.left, consumer)
  consumer(root.element)
  root.right && inorder(root.right, consumer)
}

export function postorder<E>(root: TreeNode<E>, consumer: Consumer<E>) {
  root.left && postorder(root.left, consumer)
  root.right && postorder(root.right, consumer)
  consumer(root.element)
}

export function toList<E>(root: TreeNode<E>, traverser: Traversal<E>): Array<E> {
  const list = Array<E>()
  traverser(root, e => list.push(e))
  return list
}
