#[derive(serde::Serialize, serde::Deserialize)]
pub struct R<T> {
    code: i32,
    message: String,
    data: Option<T>,
}

impl<T> R<T> {
    pub fn success(data: T) -> Self {
        Self {
            code: 0,
            message: "success".to_string(),
            data: Some(data),
        }
    }

    pub fn failure(message: String) -> Self {
        Self { code: -1, message, data: None }
    }
}
