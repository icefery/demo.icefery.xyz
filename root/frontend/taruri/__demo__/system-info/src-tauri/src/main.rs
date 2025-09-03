#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

mod system_info;

#[tauri::command]
fn system_info() -> system_info::SystemInfo {
    system_info::system_info()
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![system_info])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
