import { inorder, levelorder, postorder, preorder, toList, type TreeNode } from '../src/binary-tree'

/*
    A
   / \
  B   C
   \   \
    D   E
   / \
  F   G
*/
const tree: TreeNode<string> = {
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

const preorderList = toList(tree, preorder)
console.log(preorderList.join(' ')) // A B D F G C E

const inorderList = toList(tree, inorder)
console.log(inorderList.join(' ')) // B F D G A C E

const postorderList = toList(tree, postorder)
console.log(postorderList.join(' ')) // F G D B E C A

const levelorderList = toList(tree, levelorder)
console.log(levelorderList.join(' ')) // A B C D E F G
