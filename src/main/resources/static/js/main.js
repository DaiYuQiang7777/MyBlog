// 主页Vue应用
const { createApp, ref, onMounted } = Vue;

const app = createApp({
  setup() {
    const articles = ref([]);
    const loading = ref(true);

    // 获取文章列表
    const fetchArticles = async () => {
      try {
        loading.value = true;
        const response = await axios.get('/api/articles/published');
        articles.value = response.data;
      } catch (error) {
        console.error('获取文章列表失败:', error);
      } finally {
        loading.value = false;
      }
    };

    onMounted(() => {
      fetchArticles();
    });

    return {
      articles,
      loading,
      formatDate
    };
  }
});

// 注册ArticleCard组件
app.component('article-card', {
  props: ['article'],
  template: `
    <div class="article-card card mb-4">
      <div class="card-body">
        <h3 class="card-title article-title">
          <a :href="'/article/' + article.id" class="text-decoration-none">{{ article.title }}</a>
        </h3>
        <div class="article-meta">
          <small class="text-muted">
            作者: {{ article.authorId }} | 
            发布时间: {{ formatDate(article.publishedAt) }} | 
            浏览: {{ article.viewCount || 0 }} | 
            评论: {{ article.commentCount || 0 }}
          </small>
        </div>
        <div class="article-content">
          <p>{{ article.summary || article.content.substring(0, 200) + '...' }}</p>
        </div>
        <div class="article-footer">
          <a :href="'/article/' + article.id" class="btn btn-sm btn-outline-primary">阅读更多</a>
        </div>
      </div>
    </div>
  `
});

// 创建应用
app.mount('#app');

// 在全局作用域提供formatDate函数
function formatDate(dateString) {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  });
}