import { inorder, postorder, preorder, toList, type TreeNode } from '../src/binary-tree'

const a: TreeNode<string> = {
  element: 'A',
  left: {
    element: 'B',
    right: {
      element: 'D',
      left: {
        element: 'F'
      },
      right: {
        element: 'G'
      }
    }
  },
  right: {
    element: 'C',
    right: {
      element: 'E'
    }
  }
}

const preorderList = toList(a, preorder)
console.log(preorderList.join(' ')) // A B D F G C E

const inorderList = toList(a, inorder)
console.log(inorderList.join(' ')) // B F D G A C E

const postorderList = toList(a, postorder)
console.log(postorderList.join(' ')) // F G D B E C A
