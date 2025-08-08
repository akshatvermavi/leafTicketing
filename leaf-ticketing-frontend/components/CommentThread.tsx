import React, { useEffect, useState } from 'react';
import { useAuth } from './AuthProvider';

interface Comment {
  id: number;
  user: { username: string };
  content: string;
  createdAt: string;
}

interface CommentThreadProps {
  ticketId: number;
}

const CommentThread: React.FC<CommentThreadProps> = ({ ticketId }) => {
  const { token, user } = useAuth();
  const [comments, setComments] = useState<Comment[]>([]);
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchComments = async () => {
    setLoading(true);
    const res = await fetch(`/api/comments/ticket/${ticketId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    if (res.ok) {
      const data = await res.json();
      setComments(data);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchComments();
    // eslint-disable-next-line
  }, [ticketId]);

  const handleAddComment = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newComment.trim()) return;
    const res = await fetch('/api/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ticketId, userId: user?.id, content: newComment }),
    });
    if (res.ok) {
      setNewComment('');
      fetchComments();
    } else {
      alert('Failed to add comment');
    }
  };

  if (loading) return <div>Loading comments...</div>;

  return (
    <div>
      <h3>Comments</h3>
      <ul>
        {comments.map(comment => (
          <li key={comment.id}>
            <b>{comment.user?.username || 'User'}:</b> {comment.content} <i>({new Date(comment.createdAt).toLocaleString()})</i>
          </li>
        ))}
      </ul>
      <form onSubmit={handleAddComment} className="space-y-2">
        <textarea
          value={newComment}
          onChange={e => setNewComment(e.target.value)}
          placeholder="Add a comment..."
          required
        />
        <button type="submit">Add Comment</button>
      </form>
    </div>
  );
};

export default CommentThread; 