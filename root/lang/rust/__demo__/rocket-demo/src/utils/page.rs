#[derive(serde::Serialize, serde::Deserialize)]
#[serde(rename_all(serialize = "camelCase", deserialize = "camelCase"))]
pub struct PageRequest {
    pub size: i32,
    pub page: i32,
}

#[derive(serde::Serialize, serde::Deserialize)]
#[serde(rename_all(serialize = "camelCase", deserialize = "camelCase"))]
pub struct PageResponse<T> {
    size: i32,
    page: i32,
    total_elements: i64,
    total_pages: i32,
    content: Vec<T>,
}

impl Default for PageRequest {
    fn default() -> Self {
        Self { size: 10, page: 1 }
    }
}

impl PageRequest {
    pub fn do_page<T, F: Fn() -> Vec<T>>(&self, f: F) -> PageResponse<T> {
        let data = f();
        let total_elements = data.len() as i64;
        let total_pages = (total_elements as f64 / self.size as f64).ceil() as i32;
        let content = data
            .into_iter()
            .skip(((self.page - 1) * self.size) as usize)
            .take(self.size as usize)
            .collect::<Vec<T>>();
        return PageResponse {
            size: self.size,
            page: self.page,
            total_elements,
            total_pages,
            content,
        };
    }
}

impl<T> PageResponse<T> {
    pub fn map<R, F: Fn(&T) -> R>(&self, f: F) -> PageResponse<R> {
        let content = self.content.iter().map(f).collect::<Vec<R>>();
        PageResponse {
            size: self.size,
            page: self.page,
            total_elements: self.total_elements,
            total_pages: self.total_pages,
            content,
        }
    }
}
