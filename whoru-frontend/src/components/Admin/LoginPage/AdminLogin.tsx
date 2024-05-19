import { useAdminAuthReq, useAdminLoginReq } from '@/hooks/Auth/useAuth'
import { setRole } from '@/stores/store'
import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'

const AdminLogin = () => {
	const [id, setId] = useState<string>('');
	const dispatch = useDispatch();
	const [pw, setPassword] = useState<string>('');
	const loginMutation = useAdminLoginReq();
	const { data: userData, refetch } = useAdminAuthReq();
	const navigate = useNavigate();

  useEffect(() => {
    if (userData) {
      dispatch(setRole(userData.role));
      navigate('/admin');
    }
  }, [userData, dispatch, navigate]);

  const handleLogin = async () => {
    const formData = { id, pw };
    console.log(formData);
    loginMutation.mutate(formData, {
      onSuccess: () => {
        refetch();
      }
    });
  };

	return (
		<>
			<div className="px-4 translate-y-1/2">
				<div className="flex flex-col">
					<label className="text-[16px] pb-2 text-text_color">ID</label>
					<input
						type="text"
						placeholder="아이디를 입력해주세요"
						value={id}
						onChange={(e) => setId(e.target.value)}
						className="w-full max-w-[500px] outline-none border-b p-2 text-[14px] text-text_color"
					/>
				</div>
				<div className="flex flex-col">
					<p className="text-[16px] pb-2 pt-4 text-text_color">PassWord</p>
					<input
						type="password"
						placeholder="비밀번호를 입력해주세요"
						value={pw}
						onChange={(e) => setPassword(e.target.value)}
						className="w-full max-w-[500px] outline-none border-b p-2 text-[14px] text-text_color"
					/>
				</div>
				<div className="w-full max-w-[500px] mx-auto px-4 py-8" onClick={handleLogin}>
					<button className="bg-gray-200 w-full text-text_color rounded-[10px] p-2 text-md">로그인</button>
				</div>
			</div>
		</>
	)
}

export default AdminLogin;


