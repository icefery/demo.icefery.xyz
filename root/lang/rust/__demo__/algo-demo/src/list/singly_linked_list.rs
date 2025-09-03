struct Node<T> {
    data: T,
    next: Option<Box<Node<T>>>,
}

pub struct LinkedList<T> {
    head: Option<Box<Node<T>>>,
}

pub struct IntoIter<T>(LinkedList<T>);

impl<T> Node<T> {
    fn new(data: T) -> Self {
        Self { data, next: None }
    }
}

impl<T> LinkedList<T> {
    pub fn new() -> Self {
        Self { head: None }
    }

    pub fn push_front(&mut self, data: T) {
        let mut new_node = Box::new(Node::new(data));
        new_node.next = self.head.take();
        self.head = Some(new_node);
    }

    pub fn pop_front(&mut self) -> Option<T> {
        self.head.take().map(|it| {
            self.head = it.next;
            it.data
        })
    }

    pub fn peek_front(&self) -> Option<&T> {
        self.head.as_ref().map(|it| &it.data)
    }

    pub fn peek_front_mut(&mut self) -> Option<&mut T> {
        self.head.as_mut().map(|it| &mut it.data)
    }

    pub fn push_back(&mut self, data: T) {
        let new_node = Box::new(Node::new(data));
        match self.head.as_mut() {
            None => {
                self.head = Some(new_node);
            }
            Some(mut current) => {
                while let Some(ref mut next) = current.next {
                    current = next;
                }
                current.next = Some(new_node);
            }
        }
    }

    pub fn pop_back(&mut self) -> Option<T> {
        None
    }

    pub fn reverse(&mut self) {
        let mut prev = None;
        while let Some(mut current) = self.head.take() {
            self.head = current.next.take();
            current.next = prev;
            prev = Some(current);
        }
        self.head = prev;
    }
}

impl<T: std::fmt::Display> std::fmt::Display for LinkedList<T> {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        let mut node = &self.head;
        while let Some(current) = node {
            write!(f, "{} -> ", current.data)?;
            node = &current.next;
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
        (1..=5).for_each(|x| list.push_front(x));
        println!("{list}");
    }

    #[test]
    fn test_push_back() {
        let mut list = LinkedList::new();
        (1..=5).for_each(|x| list.push_back(x));
        println!("{list}");
    }

    #[test]
    fn test_reverse() {
        let mut list = LinkedList::new();
        (1..=5).for_each(|x| list.push_back(x));
        list.reverse();
        println!("{list}");
    }
}
