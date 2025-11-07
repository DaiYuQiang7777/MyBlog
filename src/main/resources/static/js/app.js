// 全局变量
let authToken = localStorage.getItem('authToken');
let currentUser = JSON.parse(localStorage.getItem('currentUser') || 'null');

// API基础URL
const API_BASE_URL = '/api';

// 设置Axios默认配置
axios.defaults.headers.common['Authorization'] = authToken ? `Bearer ${authToken}` : '';

// 获取文章列表
async function fetchArticles() {
    try {
        const response = await axios.get(`${API_BASE_URL}/articles/published`);
        return response.data;
    } catch (error) {
        console.error('获取文章列表失败:', error);
        return [];
    }
}

// 获取分类列表
async function fetchCategories() {
    try {
        const response = await axios.get(`${API_BASE_URL}/categories`);
        return response.data;
    } catch (error) {
        console.error('获取分类列表失败:', error);
        return [];
    }
}

// 获取标签列表
async function fetchTags() {
    try {
        const response = await axios.get(`${API_BASE_URL}/tags`);
        return response.data;
    } catch (error) {
        console.error('获取标签列表失败:', error);
        return [];
    }
}

// 用户登录
async function login(username, password) {
    try {
        const response = await axios.post(`${API_BASE_URL}/users/login`, {
            username,
            password
        });
        
        if (response.data.token) {
            authToken = response.data.token;
            currentUser = response.data.user;
            
            // 保存到本地存储
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            // 设置Axios头部
            axios.defaults.headers.common['Authorization'] = `Bearer ${authToken}`;
            
            // 更新UI
            updateUIForLoggedInUser();
            
            return { success: true, user: currentUser };
        }
    } catch (error) {
        console.error('登录失败:', error);
        return { success: false, message: error.response?.data?.message || '登录失败' };
    }
}

// 用户注册
async function register(userData) {
    try {
        const response = await axios.post(`${API_BASE_URL}/users/register`, userData);
        
        if (response.data.token) {
            authToken = response.data.token;
            currentUser = response.data.user;
            
            // 保存到本地存储
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            // 设置Axios头部
            axios.defaults.headers.common['Authorization'] = `Bearer ${authToken}`;
            
            // 更新UI
            updateUIForLoggedInUser();
            
            return { success: true, user: currentUser };
        }
    } catch (error) {
        console.error('注册失败:', error);
        return { success: false, message: error.response?.data?.message || '注册失败' };
    }
}

// 用户登出
function logout() {
    authToken = null;
    currentUser = null;
    
    // 清除本地存储
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    
    // 清除Axios头部
    delete axios.defaults.headers.common['Authorization'];
    
    // 更新UI
    updateUIForLoggedOutUser();
    
    // 重定向到首页
    window.location.href = '/';
}

// 更新登录用户的UI
function updateUIForLoggedInUser() {
    document.getElementById('loginLink').style.display = 'none';
    document.getElementById('registerLink').style.display = 'none';
    document.getElementById('logoutLink').style.display = 'block';
    document.getElementById('userMenu').style.display = 'block';
}

// 更新登出用户的UI
function updateUIForLoggedOutUser() {
    document.getElementById('loginLink').style.display = 'block';
    document.getElementById('registerLink').style.display = 'block';
    document.getElementById('logoutLink').style.display = 'none';
    document.getElementById('userMenu').style.display = 'none';
}

// 检查用户认证状态
function checkAuthStatus() {
    if (authToken && currentUser) {
        updateUIForLoggedInUser();
    } else {
        updateUIForLoggedOutUser();
    }
}

// 发布文章
async function createArticle(articleData) {
    try {
        const response = await axios.post(`${API_BASE_URL}/articles`, articleData, {
            headers: {
                'Authorization': `Bearer ${authToken}`,
                'Content-Type': 'application/json'
            }
        });
        
        return { success: true, article: response.data };
    } catch (error) {
        console.error('发布文章失败:', error);
        return { success: false, message: error.response?.data?.message || '发布文章失败' };
    }
}

// 提交评论
async function submitComment(commentData) {
    try {
        const response = await axios.post(`${API_BASE_URL}/comments`, commentData, {
            headers: {
                'Authorization': `Bearer ${authToken}`,
                'Content-Type': 'application/json'
            }
        });
        
        return { success: true, comment: response.data };
    } catch (error) {
        console.error('提交评论失败:', error);
        return { success: false, message: error.response?.data?.message || '提交评论失败' };
    }
}

// 格式化日期
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// 检查用户是否已登录
function isLoggedIn() {
    return authToken !== null && currentUser !== null;
}

// 初始化认证状态
document.addEventListener('DOMContentLoaded', function() {
    checkAuthStatus();
});