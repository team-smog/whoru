export interface IUserInfo {
  id: number | null;
  userName: string | null;
  provider: string | null;
  memberIdentifier: string | null;
  boxCount: number | null;
  role: string | null;
  createDate: string | null;
  reportCount: number | null;
  languageType: string | null;
  iconUrl: string | null;
  fcmToken: string | null;
  pushAlarm: boolean | null;
}