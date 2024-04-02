#[derive(serde::Serialize, serde::Deserialize, Debug)]
pub struct DiskInfo {
    pub disk_mount: String,
    pub disk_total: f32,
    pub disk_used: f32,
    pub disk_available: f32,
}

#[derive(serde::Serialize, serde::Deserialize, Debug)]
pub struct SystemInfo {
    pub hostname: String,
    pub memory_total: f32,
    pub memory_used: f32,
    pub memory_available: f32,
    pub memory_free: f32,
    pub swap_total: f32,
    pub swap_used: f32,
    pub swap_free: f32,
    pub cpu_usage: f32,
    pub disks: Vec<DiskInfo>,
}

pub fn system_info() -> SystemInfo {
    let hostname = sysinfo::System::host_name().unwrap();
    let mut system = sysinfo::System::new_all();

    system.refresh_all();

    // 内存
    let memory_total = system.total_memory() as f32 / (1 << 30) as f32;
    let memory_used = system.used_memory() as f32 / (1 << 30) as f32;
    let memory_available = system.available_memory() as f32 / (1 << 30) as f32;
    let memory_free = system.free_memory() as f32 / (1 << 30) as f32;
    let swap_total = system.total_swap() as f32 / (1 << 30) as f32;
    let swap_used = system.used_swap() as f32 / (1 << 30) as f32;
    let swap_free = system.free_swap() as f32 / (1 << 30) as f32;
    // CPU
    let cpu_usage = system.global_cpu_info().cpu_usage() / 100f32;
    let cpu_usage = if cpu_usage.is_nan() { 0.00 } else { cpu_usage };
    // 磁盘
    let disks = sysinfo::Disks::new_with_refreshed_list();
    let disks = disks
        .iter()
        .map(|disk| {
            let disk_mount = disk.mount_point().to_str().unwrap().to_string();
            let disk_total = disk.total_space() as f32 / (1 << 30) as f32;
            let disk_used = (disk.total_space() - disk.available_space()) as f32 / (1 << 30) as f32;
            let disk_available = disk.available_space() as f32 / (1 << 30) as f32;
            DiskInfo {
                disk_mount,
                disk_total,
                disk_used,
                disk_available,
            }
        })
        .collect::<Vec<DiskInfo>>();

    SystemInfo {
        hostname,
        memory_total,
        memory_used,
        memory_available,
        memory_free,
        swap_total,
        swap_used,
        swap_free,
        cpu_usage,
        disks,
    }
}
