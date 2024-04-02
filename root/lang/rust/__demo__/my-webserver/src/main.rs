use std::{
    fs,
    io::{prelude::*, BufReader},
    net::{TcpListener, TcpStream},
    thread,
    time::Duration,
};

use my_webserver::ThreadPool;

fn main() {
    let listener = TcpListener::bind("127.0.0.1:7878").unwrap();
    let pool = ThreadPool::new(4);

    for stream in listener.incoming() {
        let stream = stream.unwrap();
        pool.execute(|| handle_connection(stream));
    }

    println!("Shutting down.");
}

fn handle_connection(mut stream: TcpStream) {
    // 请求行
    let request_line = BufReader::new(&mut stream).lines().next().unwrap().unwrap();

    // (状态行, 文件名)
    let (status_line, filename) = match &request_line[..] {
        "GET / HTTP/1.1" => ("HTTP/1.1 200 OK", "public/index.html"),
        "GET /sleep HTTP/1.1" => {
            thread::sleep(Duration::from_secs(5));
            ("HTTP/1.1 200 OK", "public/index.html")
        }
        _ => ("HTTP/1.1 404 NOT FOUND", "public/404.html"),
    };

    let content = fs::read_to_string(filename).unwrap();
    let headers = format!("Content-Type: text/html\r\nContent-Length: {}", content.len());
    let response = format!("{status_line}\r\n{headers}\r\n\r\n{content}");

    stream.write_all(response.as_bytes()).unwrap();
    stream.flush().unwrap();
}
