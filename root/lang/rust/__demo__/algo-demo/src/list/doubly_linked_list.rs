use std::cell::{Ref, RefCell, RefMut};
use std::rc::Rc;

struct Node<T> {
    data: T,
    next: Option<Rc<RefCell<Node<T>>>>,
    prev: Option<Rc<RefCell<Node<T>>>>,
}

pub struct LinkedList<T> {
    head: Option<Rc<RefCell<Node<T>>>>,
    tail: Option<Rc<RefCell<Node<T>>>>,
}

impl<T> Node<T> {
    fn new(data: T) -> Self {
        Self {
            data,
            next: None,
            prev: None,
        }
    }
}

impl<T> LinkedList<T> {
    pub fn new() -> Self {
        Self {
            head: None,
            tail: None,
        }
    }

    pub fn push_front(&mut self, data: T) {
        let new_head = Rc::new(RefCell::new(Node::new(data)));
        match self.head.take() {
            None => {
                self.tail = Some(new_head.clone());
                self.head = Some(new_head);
            }
            Some(old_head) => {
                old_head.borrow_mut().prev = Some(new_head.clone());
                new_head.borrow_mut().next = Some(old_head);
                self.head = Some(new_head.clone());
            }
        }
    }

    pub fn pop_front(&mut self) -> Option<T> {
        self.head.take().map(|old_head| {
            match old_head.borrow_mut().next.take() {
                None => {
                    self.tail.take();
                }
                Some(new_head) => {
                    new_head.borrow_mut().prev.take();
                    self.head = Some(new_head);
                }
            }
            Rc::try_unwrap(old_head).ok().unwrap().into_inner().data
        })
    }

    pub fn peek_front(&self) -> Option<Ref<T>> {
        self.head
            .as_ref()
            .map(|node| Ref::map(node.borrow(), |node| &node.data))
    }

    pub fn peek_front_mut(&mut self) -> Option<RefMut<T>> {
        self.head
            .as_ref()
            .map(|node| RefMut::map(node.borrow_mut(), |node| &mut node.data))
    }

    pub fn push_back(&mut self, data: T) {
        let new_tail = Rc::new(RefCell::new(Node::new(data)));
        match self.tail.take() {
            None => {
                self.head = Some(new_tail.clone());
                self.tail = Some(new_tail);
            }
            Some(old_tail) => {
                new_tail.borrow_mut().prev = Some(old_tail.clone());
                old_tail.borrow_mut().next = Some(new_tail.clone());
                self.tail = Some(new_tail.clone());
            }
        }
    }

    pub fn pop_back(&mut self) -> Option<T> {
        self.tail.take().map(|old_tail| {
            match old_tail.borrow_mut().prev.take() {
                None => {
                    self.head.take();
                }
                Some(new_tail) => {
                    new_tail.borrow_mut().next.take();
                    self.tail = Some(new_tail);
                }
            };
            Rc::try_unwrap(old_tail).ok().unwrap().into_inner().data
        })
    }

    pub fn peek_back(&self) -> Option<Ref<T>> {
        self.tail
            .as_ref()
            .map(|node| Ref::map(node.borrow(), |node| &node.data))
    }

    pub fn peek_back_mut(&mut self) -> Option<RefMut<T>> {
        self.tail
            .as_ref()
            .map(|node| RefMut::map(node.borrow_mut(), |node| &mut node.data))
    }

    pub fn reverse(&mut self) {
        let mut current = self.head.take();
        self.head = self.tail.take();
        while let Some(current_node) = current {
            let next_node = current_node.borrow().next.clone();
            let mut current_node_ref = current_node.borrow_mut();
            current_node_ref.next = current_node_ref.prev.take();
            if let Some(ref next_node_ref) = next_node {
                next_node_ref.borrow_mut().prev = Some(current_node.clone());
            } else {
                self.tail = Some(current_node.clone());
            }
            current = next_node;
        }
    }
}

impl<T: std::fmt::Display> std::fmt::Display for LinkedList<T> {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        let mut node = self.head.clone();
        while let Some(current) = node {
            write!(f, "{} -> ", current.borrow().data)?;
            node = current.borrow().next.clone();
        }
        write!(f, "None")
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_push_front() {
        let mut list = LinkedList::new();
        let total = 5;
        for x in 1..=total {
            list.push_front(x);
            println!("list={list}");
        }
    }

    #[test]
    fn test_pop_front() {
        let mut list = LinkedList::new();
        let total = 5;
        (1..=total).for_each(|x| list.push_front(x));
        for x in 1..=total {
            let front = list.pop_front().unwrap();
            println!(" front={front} list={list}");
        }
    }

    #[test]
    fn test_peek_front() {
        let mut list = LinkedList::new();
        let total = 5;
        (1..=total).for_each(|x| list.push_front(x));
        for x in 1..=total {
            let front = list.peek_front().unwrap().clone();
            println!(" front={front} list={list}");
        }
    }

    #[test]
    fn test_push_back() {
        let mut list = LinkedList::new();
        let total = 5;
        for x in 1..=total {
            list.push_back(x);
            println!("list={list}");
        }
    }

    #[test]
    fn test_pop_back() {
        let mut list = LinkedList::new();
        let total = 5;
        (1..=total).for_each(|x| list.push_back(x));
        for x in 1..=total {
            let front = list.pop_back().unwrap();
            println!(" front={front} list={list}");
        }
    }

    #[test]
    fn test_peek_back() {
        let mut list = LinkedList::new();
        let total = 5;
        (1..=total).for_each(|x| list.push_back(x));
        for x in 1..=total {
            let front = list.peek_back().unwrap();
            println!(" front={front} list={list}");
        }
    }

    #[test]
    fn test_reverse() {
        let mut list = LinkedList::new();
        let total = 5;
        (1..=total).for_each(|x| list.push_back(x));
        println!("list={list}");
        list.reverse();
        println!("list={list}");
    }
}
