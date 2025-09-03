export interface TreeNode<T> {
  label: string
  data: T
  children: TreeNode<T>[]
}

export function buildTree<T extends { [key: string]: any }, K extends keyof T>(data: T[], key: K, separator: string): TreeNode<T>[] {
  const roots = [] as TreeNode<T>[]
  for (const item of data) {
    const paths = item[key] as string
    let curr = roots
    for (const path of paths.split(separator)) {
      const temp = curr
      for (const node of curr) {
        if (node.label === path) {
          curr = node.children
          break
        }
      }
      if (curr == temp) {
        const root = { label: path, data: item, children: [] } as TreeNode<T>
        curr.push(root)
        curr = root.children
      }
    }
  }
  return roots
}
