#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <thread>
#include <chrono>
#include <csignal>
#include <ctime>
#include <iomanip>
#include <sys/statvfs.h>

bool running = true;

void signalHandler(int) {
    running = false;
}

std::string getTimeNow() {
    std::time_t now = std::time(nullptr);
    std::stringstream ss;
    ss << std::put_time(std::localtime(&now), "%Y-%m-%d %H:%M:%S");
    return ss.str();
}

std::string getTemperature() {
    std::ifstream file("/sys/class/thermal/thermal_zone0/temp");
    if (!file) return "N/A";
    int temp_milli;
    file >> temp_milli;
    return std::to_string(temp_milli / 1000.0) + "°C";
}

float getCPUUsage() {
    std::ifstream file("/proc/stat");
    std::string line;
    std::getline(file, line);
    std::istringstream ss(line);

    std::string cpu;
    size_t user, nice, system, idle, iowait, irq, softirq;
    ss >> cpu >> user >> nice >> system >> idle >> iowait >> irq >> softirq;

    static size_t last_total = 0, last_idle = 0;
    size_t idle_all = idle + iowait;
    size_t total = user + nice + system + idle_all + irq + softirq;
    size_t diff_total = total - last_total;
    size_t diff_idle = idle_all - last_idle;

    last_total = total;
    last_idle = idle_all;

    if (diff_total == 0) return 0;
    return 100.0f * (diff_total - diff_idle) / diff_total;
}

std::string getMemory() {
    std::ifstream file("/proc/meminfo");
    std::string label;
    size_t mem_total, mem_free, buffers, cached;

    while (file >> label) {
        if (label == "MemTotal:") file >> mem_total;
        else if (label == "MemFree:") file >> mem_free;
        else if (label == "Buffers:") file >> buffers;
        else if (label == "Cached:") {
            file >> cached;
            break;
        } else file.ignore(1024, '\n');
    }

    size_t used = mem_total - (mem_free + buffers + cached);
    std::stringstream ss;
    ss << used / 1024 << " / " << mem_total / 1024 << " MB";
    return ss.str();
}

std::string getDisk() {
    struct statvfs buf;
    if (statvfs("/", &buf) != 0) return "N/A";
    size_t total = buf.f_blocks * buf.f_frsize;
    size_t used = (buf.f_blocks - buf.f_bfree) * buf.f_frsize;
    std::stringstream ss;
    ss << used / (1024 * 1024 * 1024) << " / "
       << total / (1024 * 1024 * 1024) << " GB";
    return ss.str();
}

std::pair<size_t, size_t> getNet() {
    std::ifstream file("/proc/net/dev");
    std::string line;
    size_t rx_total = 0, tx_total = 0;

    while (std::getline(file, line)) {
        if (line.find(":") == std::string::npos) continue;
        std::istringstream ss(line);
        std::string iface;
        size_t rx, tx;
        ss >> iface >> rx;
        for (int i = 0; i < 7; ++i) ss >> tx; // Skip fields
        ss >> tx;
        rx_total += rx;
        tx_total += tx;
    }

    return {rx_total, tx_total};
}

int main() {
    std::signal(SIGINT, signalHandler);

    auto [last_tx, last_rx] = getNet();

    while (running) {
        system("clear");

        std::cout << "ROCK 5B CLI DASHBOARD - " << getTimeNow() << "\n\n";
        std::cout << "CPU Usage:   " << std::fixed << std::setprecision(1) << getCPUUsage() << "%\n";
        std::cout << "Temp:        " << getTemperature() << "\n";
        std::cout << "Memory:      " << getMemory() << "\n";
        std::cout << "Disk:        " << getDisk() << "\n";

        auto [rx, tx] = getNet();
        std::cout << "Net:         ↑ " << (tx - last_tx) / 1024 << " KB/s   ↓ " << (rx - last_rx) / 1024 << " KB/s\n";

        last_rx = rx;
        last_tx = tx;

        std::this_thread::sleep_for(std::chrono::seconds(1));
    }

    std::cout << "\nExited dashboard.\n";
    return 0;
}
