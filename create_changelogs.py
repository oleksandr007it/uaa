import argparse
import re
import subprocess
import requests
import yaml
import os
import sys

PATTERN = re.compile(r"v\d+.\d+.\d+")
PROJECTS_IDS = {
    'uaa': 38,
}

GITLAB_TOKEN = os.environ['DEPLOYMENT_API_TOKEN']
project_id = os.environ['CI_PROJECT_ID']
URL = 'https://git.customs.net.ua/api/v4/projects/' + \
    project_id + '/repository/tags'
SLACK_API_URL = 'https://slack.com/api/chat.postMessage'
VERSIONS_FILE = "staging-values.yml"


class EmptyMessageException(Exception):
    pass


class VersionHandler:
    def __init__(self, _old_ver, _cur_ver, _name):
        self.old_ver = _old_ver
        self.cur_ver = _cur_ver
        self.name = _name
        self.messages = []
        self.get_diff()

    def get_diff(self):
        if self.old_ver == self.cur_ver:
            return
        res = requests.get(
            URL.format(project_id=PROJECTS_IDS[self.name]),
            headers={'PRIVATE-TOKEN': GITLAB_TOKEN}
        )
        for tag in res.json():
            if tag['name'] == self.old_ver:
                return
            self.messages.append(
                " ### {}".format(tag['message'].replace('\n', '\n\t'))
            )

    def __repr__(self):
        return f"Version(_old_ver={self.old_ver}, " \
            f"_cur_ver={self.cur_ver}, _name={self.name})"


def get_old_file_version():
    version = subprocess.getoutput('git describe --abbrev=0')
    if not version:
        raise AttributeError('Undefined old version')
    return yaml.safe_load(
        subprocess.getoutput(f'git show {version}:{VERSIONS_FILE}')
    )


def get_cur_file_version():
    with open(VERSIONS_FILE) as f:
        return yaml.safe_load(f)


def validate_version(version):
    if not PATTERN.match(version):
        raise argparse.ArgumentTypeError(
            'Forbidden format. Call -h for more information')
    return version


def get_img_block(block, path):
    for part in path.split("/"):
        block = block.get(part)
    return block


def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--version', '-v',
        help='Tag version in format vX.X.X',
        type=validate_version,
        required=True
    )
    parser.add_argument(
        '--notes', '-n',
        help='Post notes to slack and repo',
        nargs='?',
        type=bool,
        required=False,
        default=False,
        const=True
    )
    return parser.parse_args()


def post_release_notes(tag, message_to_post):
    deployment_project_id = os.environ['CI_PROJECT_ID']
    response = requests.post(
        f"{URL.format(project_id=deployment_project_id)}/{tag}/release",
        json={'description': message_to_post},
        headers={'PRIVATE-TOKEN': GITLAB_TOKEN}
    )
    if response.status_code != 201:
        print(f'Something went wrong data:\n{response.json()}')


# def post_request(channel_id, token, tag, mess):
#     response = requests.post(
#         SLACK_API_URL,
#         json={
#             'channel': channel_id,
#             'text': f"<!here> New version {tag} "
#             f"released on staging \n```{mess}```"
#         },
#         headers={'Authorization': f'Bearer {token}'}
#     )
#     if response.status_code != 200:
#         print(f'Something went wrong data:\n{response.json()}')


# def send_message_to_slack(cur_tag, message_to_send):
#     channel_id = os.environ['SLACK_CHANEL_ID']
#     bot_token = os.environ['SLACK_TOKEN']
#     post_request(channel_id, bot_token, cur_tag, message_to_send)

#     if "RG_SLACK_CHANEL_ID" in os.environ:
#         rg_channel_id = os.environ['RG_SLACK_CHANEL_ID']
#         rg_bot_token = os.environ['RG_SLACK_TOKEN']
#         post_request(rg_channel_id, rg_bot_token, cur_tag, message_to_send)


if __name__ == '__main__':
    cur_tag = parse_args().version
    post_notes = parse_args().notes
    if post_notes:
        cur_tag = cur_tag.replace('-', '.')
        message = subprocess.getoutput(
            f"git tag -l --format='%(contents)' {cur_tag}")
        if not message:
            raise EmptyMessageException

        post_release_notes(cur_tag, message)
        # send_message_to_slack(cur_tag, message)

        print('Success!')
        sys.exit(0)

    services_objects = {}
    old_file = get_old_file_version()
    cur_file = get_cur_file_version()
    for service in PROJECTS_IDS.keys():
        old_image_block = get_img_block(old_file, service)
        cur_image_block = get_img_block(cur_file, service)
        if not old_image_block or not cur_image_block:
            continue
        services_objects[service] = VersionHandler(
            old_image_block['image']['tag'].replace('-', '.'),
            cur_image_block['image']['tag'].replace('-', '.'),
            service
        )

    file_name = f'Changelog-{cur_tag}.txt'
    with open(file_name, 'a') as fp:
        result = ['\n']
        for service, data in services_objects.items():
            if data.messages:
                result.append(
                    f" ## {service} was updated from {data.old_ver} to {data.cur_ver}")
                result.append('\n\n'.join(data.messages))
            else:
                result.append(
                    f" ## {service} was not updated, current version {data.cur_ver}")
            result.append('\n')
        fp.writelines('\n'.join(result))
