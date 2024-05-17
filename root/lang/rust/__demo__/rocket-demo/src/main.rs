use rocket::serde::json::Json;

use crate::utils::page::{PageRequest, PageResponse};
use crate::utils::r::R;

mod utils;

#[rocket::main]
async fn main() -> Result<(), rocket::Error> {
    let _rocket = rocket::build().mount("/", rocket::routes![find]).launch().await?;
    Ok(())
}

#[derive(serde::Serialize, serde::Deserialize)]
struct Article {
    title: String,
    author: String,
    content: String,
}

#[rocket::get("/find?<size>&<page>")]
async fn find(size: i32, page: i32) -> Json<R<PageResponse<Article>>> {
    let page_response = PageRequest { size, page }.do_page(|| {
        (1..=100)
            .into_iter()
            .map(|e| Article {
                title: format!("article-{}", e),
                author: format!("author-{}", e),
                content: format!("content-{}", e),
            })
            .collect::<Vec<Article>>()
    });
    let r = R::success(page_response);
    Json(r)
}
