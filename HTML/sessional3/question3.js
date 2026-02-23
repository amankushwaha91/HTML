const fetchUser = (userId) => {
  return new Promise((resolve, reject) => {
    console.log("--- Fetching User Data... ---");
    setTimeout(() => {
      const user = { id: userId, username: "Dev_Expert" };
      user ? resolve(user) : reject("User not found!");
    }, 1000);
  });
};

const fetchPosts = (username) => {
  return new Promise((resolve, reject) => {
    console.log(`--- Fetching posts for ${username}... ---`);
    setTimeout(() => {
      const posts = ["Post 1: Hello World", "Post 2: Learning JS"];
      const success = true;
      success ? resolve(posts) : reject("Failed to load posts.");
    }, 1000);
  });
};

fetchUser(101)
  .then((user) => {
    console.log("User retrieved:", user.username);
    return fetchPosts(user.username);
  })
  .then((posts) => {
    console.log("Posts Displayed:", posts);
  })
  .catch((error) => {
    console.error("ERROR:", error);
  })
  .finally(() => {
    console.log("Operation complete.");
  });
